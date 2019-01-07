
python -m venv venv

venv\Scripts\activate.bat (Windows)

source venv/bin/activate (Linux)

pip install -r requirements.txt

python manage.py migrate --settings=MemoristAPI.settings.base

python3 manage.py runserver --settings=MemoristAPI.settings.base
