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


Phase 1 — Minimal Interpreter

Goal: Support numbers + print

Interpret:

a = 5
print(a)

✅ Phase 2 — Strings

Add:

name = "Livy"
print("Hello " + name)

✅ Phase 3 — Lists & Dictionary

Support indexing and storage

nums = [1,2,3]
user = {"name":"Livy","age":22}

✅ Phase 4 — If Statements
✅ Phase 5 — Loops (for, while)
✅ Phase 6 — Errors & Semantic checks
✅ Phase 7 — Optional functions

| Component                | Purpose                         |
| ------------------------ | ------------------------------- |
| **Lexer**                | Turns code text → tokens        |
| **Parser**               | Tokens → AST (syntax tree)      |
| **AST**                  | Represents code structure       |
| **Executor/Interpreter** | Runs the AST                    |
| **Symbol Table**         | Stores variables                |
| **Runtime**              | Built-in functions like `print` |

