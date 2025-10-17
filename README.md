# PythonReader
--------------

A simple web IDE that reads and runs Python code.


| Part                                    | Responsibility                                      |
| --------------------------------------- | --------------------------------------------------- |
| **Frontend (HTML/CSS/JS)**              | Web IDE UI, editor, syntax highlighting             |
| **Backend (Java server, no framework)** | Parses and executes Python-like code                |
| **Interpreter in Java**                 | Custom classes: Lexer → Parser → AST → Executor     |
| **Data System**                         | Store variables like numbers, strings, lists, dicts |
| **Runtime**                             | Handle print, loops, functions later                |


