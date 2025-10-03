# PythonReader
--------------

A simple Python script that reads and runs another Python file.
Useful for quickly testing code inside an IDE or terminal.

## 📌 What You Need

* Python 3.8 or newer
* Any text editor or IDE (VS Code, PyCharm, IDLE, etc.)

## 📂 Files

```
reader.py        # The script that runs other Python files
sample_code.py   # Example code to test with
```

## ▶️ How to Use

1. Put the code you want to run inside `sample_code.py`
   Example:

   ```python
   print("Hello from sample_code.py!")
   for i in range(3):
       print("Count:", i)
   ```

2. Run the reader in your terminal or IDE:

   ```bash
   python3 reader.py sample_code.py
   ```

3. You’ll see the output from `sample_code.py` printed in the terminal.

---
