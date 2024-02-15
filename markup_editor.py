output = []
while True:
    q = input('Choose a formatter: ')
    if q == '!help':
        print('Available formatters: plain bold italic header link inline-code ordered-list unordered-list new-line')
        print('Special commands: !help !done')
    elif q == '!done':
        with open('README.md', 'w') as file:
            for el in output:
                file.write(el)
        break
    elif q == 'plain':
        text = input('Text: ')
        output.append(text)
    elif q == 'bold':
        text = input('Text: ')
        output.append(f'**{text}**')
    elif q == 'italic':
        text = input('Text: ')
        output.append(f'*{text}*')
    elif q == 'header':
        lvl = int(input('Level: '))
        if 1 <= lvl <= 6:
            text = input('Text: ')
            output.append(f"{'#'*lvl} {text}\n")
        else:
            print('The level should be within the range of 1 to 6')
            continue
    elif q == 'link':
        lbl = input('Label: ')
        url = input('URL: ')
        output.append(f'[{lbl}]({url})')
    elif q == 'inline-code':
        text = input('Text: ')
        output.append(f'`{text}`')
    elif q == 'ordered-list' or q == 'unordered-list':
        while True:
            num = int(input('Number of rows: '))
            if num > 0:
                break
            print('The number of rows should be greater than zero')
        for i in range(num):
            row = input(f'Row #{i+1}: ')
            if q == 'ordered-list':
                output.append(f'{i+1}. {row}\n')
            else:
                output.append(f'* {row}\n')
    elif q == 'new-line':
        output.append('\n')
    else:
        print('Unknown formatting type or command')
    if output:
        for line in output:
            print(line, end='')
    print()
