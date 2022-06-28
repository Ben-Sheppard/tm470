from emotion_detection import detect
from fastapi import FastAPI
from pydantic import BaseModel

import logging

logger = logging.getLogger(__name__)
app = FastAPI()


@app.get("/emotion-detect")
def read_emotion(content: str):
    print("Beginning detection for text: ", content)
    prediction = detect(content)

    return prediction

