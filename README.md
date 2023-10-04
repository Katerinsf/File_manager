# File_manager
Реализация программ для управления файловой системой

- Для написания программ использовалась версия Java 8.
- Для форматирования кода использовались стандарты Oracle.
- Код каждого задания находиться в папке src в соответствующей директории.


### Exercise 00 – File Signatures

В данном задании реализовано приложение для анализа подписей произвольных файлов. 
Эта подпись позволяет определить тип содержимого файла и состоит из набора «магических чисел». 
Эти номера обычно расположены в начале файла. 
Например, подпись для типа файла PNG представлена первыми восемью байтами файла, 
которые одинаковы для всех изображений PNG:
```
89 50 4E 47 0D 0A 1A 0A
```

Это приложение принимает на вход файл signal.txt, который содержит список типов файлов 
и их соответствующие подписи в формате HEX. 
Пример:

```
PNG, 89 50 4E 47 0D 0A 1A 0A
GIF, 47 49 46 38 37 61
```
Во время выполнения программа принимает полные пути к файлам на жестком диске и сохраняет тип, 
которому соответствует подпись файла. 
Результат выполнения программы записывается в файл result.txt.
Если сигнатура этого файла не содержится в signatures.txt, то программа выведет "UNDEFINED".

Пример работы программы:

```
$java Program
-> C:/Users/Admin/images.png
PROCESSED
-> C:/Users/Admin/Games/WoW.iso
PROCESSED
-> 42
```


### Exercise 01 – Words

Данная программа определяет уровень сходства между текстами, анализируя частоту встречаемости одних и тех же слов.

На вход принимаются 2 файла с текстом, а затем выводится результат их сравнения. 

Например, есть два следующих текста:

```
1. aaa bba bba a ссс
2. bba a a a bb xxx
```
Создадается словарь, содержащий все слова в этих текстах:
```
a, aaa, bb, bba, ccc, xxx
```
Затем определяются два вектора длиной, равной длине словаря. 
В i-й позиции каждого вектора находится частота встречаемости i-го слова в словаре в 1 и 2 текстах:
```
A = (1, 1, 0, 2, 1, 0)
B = (3, 0, 1, 1, 0, 1)
```

Теперь определяется сходство векторов по следующей формуле:
![formula](misc/images/formula.png)

Таким образом, величина сходства для этих векторов равна:
```
Numerator A. B = (1 * 3 + 1 * 0 + 0 * 1 + 2 * 1 + 1 * 0 + 0 * 1) = 5
Denominator ||A|| * ||B|| = sqrt(1 * 1 + 1 * 1 + 0 * 0 + 2  * 2 + 1 * 1 + 0 * 0) * sqrt(3 * 3 + 0 * 0 + 1 * 1 + 1 * 1  + 0 * 0 + 1 * 1) = sqrt(7) * sqrt(12) = 2.64 * 3.46 = 9.1
similarity = 5 / 9.1 = 0.54
```

Пример работы программы:
```
$ java Program inputA.txt inputB.txt
Similarity = 0.54
```


### Exercise 02 – File Manager

Реализована утилита для работы с файлами. 
Приложение отображает информацию о файлах, содержимом и размере папок, 
а также обеспечивает функцию перемещения/переименования. 
По сути, приложение эмулирует командную строку Unix-подобных систем.

Программа принимает в качестве аргумента абсолютный путь к папке, 
где мы начинаем работать, и поддерживать следующие команды:

- `mv` WHAT WHERE – позволяет перенести или переименовать файл, если WHERE содержит имя файла без пути
- `ls` – отображает текущее содержимое папки (имена и размеры файлов и подпапок в КБ)
- `cd FOLDER_NAME` – изменяет текущий каталог

Например, на диске C:/ (или в корневом каталоге, в зависимости от ОС) имеется папка MAIN со следующей иерархией:
- MAIN
  + folder1
    * image.jpg
    *	animation.gif
  +	folder2
    * text.txt
    *	Program.java

Пример работы программы для каталога MAIN:
```
$ java Program --current-folder=C:/MAIN
C:/MAIN
-> ls
folder1 60 KB
folder2 90 KB
-> cd folder1
C:/MAIN/folder1
-> ls
image.jpg 10 KB
animation.gif 50 KB
-> mv image.jpg image2.jpg
-> ls
image2.jpg 10 KB
animation.gif 50 KB
-> mv animation.gif ../folder2
-> ls
image2.jpg 10 KB
-> cd ../folder2
C:/MAIN/folder2
-> ls
text.txt 10 KB
Program.java 80 KB
animation.gif 50 KB
-> exit
```

