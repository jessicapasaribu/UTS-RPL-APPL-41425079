from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session
from database import SessionLocal, engine
import models
from fastapi.middleware.cors import CORSMiddleware

models.Base.metadata.create_all(bind=engine)

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.post("/payments")
def create_payment(patient_name: str, amount: int, db: Session = Depends(get_db)):
    payment = models.Payment(patient_name=patient_name, amount=amount)
    db.add(payment)
    db.commit()
    db.refresh(payment)
    return payment

@app.put("/payments/{id}/pay")
def pay(id: int, db: Session = Depends(get_db)):
    payment = db.query(models.Payment).get(id)
    payment.status = "paid"
    db.commit()
    return payment