from transformers import pipeline
import logging

logger = logging.getLogger(__name__)

classifier = pipeline(
    "text-classification",
    model='bhadresh-savani/distilbert-base-uncased-emotion',
    return_all_scores=True
)


def detect(text):
    prediction = classifier(text, )
    print("Found results: ", prediction)
    return prediction
