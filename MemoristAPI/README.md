
python(python3) -m venv venv

venv\Scripts\activate.bat (Windows)

source venv/bin/activate (Linux)

pip install -r requirements.txt

python(python3) manage.py migrate --settings=MemoristAPI.settings.base

python(python3) manage.py runserver --settings=MemoristAPI.settings.base
