FROM python:3.9

WORKDIR /code

RUN pip install "poetry==1.0.0"

COPY ./pyproject.toml ./poetry.lock /code/

RUN poetry config virtualenvs.create false \
  && poetry install --no-dev --no-interaction --no-ansi

COPY ./emotion_detection.py ./main.py /code/

EXPOSE 8085

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8085"]
