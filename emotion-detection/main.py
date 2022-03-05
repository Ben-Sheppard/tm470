from emotion_detection import detect
from fastapi import FastAPI
from pydantic import BaseModel

import logging

logger = logging.getLogger(__name__)
app = FastAPI()


class Candidate(BaseModel):
    text: str

@app.get("/")
def read_root():
    print("home...")
    return {"Hello": "World"}


@app.post("/emotion-detect")
def read_emotion(candidate: Candidate):
    print("Beginning. detection..")
    prediction = detect(candidate.text)

    return prediction


