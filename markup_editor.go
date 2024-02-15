package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	output := []string{}
	scanner := bufio.NewScanner(os.Stdin)

	for {
		fmt.Print("Choose a formatter: ")
		scanner.Scan()
		q := scanner.Text()

		if q == "!help" {
			fmt.Println("Available formatters: plain bold italic header link inline-code ordered-list unordered-list new-line")
			fmt.Println("Special commands: !help !done")
		} else if q == "!done" {
			err := os.WriteFile("README.md", []byte(strings.Join(output, "")), 0644)
			if err != nil {
				fmt.Println("Error writing to file:", err)
			}
			break
		} else if q == "plain" || q == "bold" || q == "italic" || q == "header" ||
			q == "link" || q == "inline-code" || q == "ordered-list" || q == "unordered-list" ||
			q == "new-line" {
			if q == "plain" || q == "bold" || q == "italic" || q == "header" || q == "inline-code" {
				fmt.Print("Text: ")
				scanner.Scan()
				text := scanner.Text()
				if q == "header" {
					fmt.Print("Level: ")
					scanner.Scan()
					level, err := strconv.Atoi(scanner.Text())
					if err != nil || level < 1 || level > 6 {
						fmt.Println("The level should be within the range of 1 to 6")
						continue
					}
					output = append(output, strings.Repeat("#", level)+" "+text+"\n")
				} else {
					output = append(output, formatText(q, text))
				}
			} else if q == "link" {
				fmt.Print("Label: ")
				scanner.Scan()
				lbl := scanner.Text()
				fmt.Print("URL: ")
				scanner.Scan()
				url := scanner.Text()
				output = append(output, "["+lbl+"]("+url+")")
			} else if q == "ordered-list" || q == "unordered-list" {
				for {
					fmt.Print("Number of rows: ")
					scanner.Scan()
					num, err := strconv.Atoi(scanner.Text())
					if err != nil || num <= 0 {
						fmt.Println("The number of rows should be greater than zero")
						continue
					}
					for i := 0; i < num; i++ {
						fmt.Printf("Row #%d: ", i+1)
						scanner.Scan()
						row := scanner.Text()
						if q == "ordered-list" {
							output = append(output, fmt.Sprintf("%d. %s\n", i+1, row))
						} else {
							output = append(output, fmt.Sprintf("* %s\n", row))
						}
					}
					break
				}
			} else if q == "new-line" {
				output = append(output, "\n")
			}
		} else {
			fmt.Println("Unknown formatting type or command")
		}

		if len(output) > 0 {
			for _, line := range output {
				fmt.Print(line)
			}
		}
		fmt.Println()
	}
}

func formatText(format, text string) string {
	switch format {
	case "plain":
		return text
	case "bold":
		return "**" + text + "**"
	case "italic":
		return "*" + text + "*"
	case "inline-code":
		return "`" + text + "`"
	default:
		return ""
	}
}
