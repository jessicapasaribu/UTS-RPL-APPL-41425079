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

@app.post("/appointments")
def create_appointment(patient_name: str, doctor_name: str, date: str, db: Session = Depends(get_db)):
    appt = models.Appointment(
        patient_name=patient_name,
        doctor_name=doctor_name,
        date=date
    )
    db.add(appt)
    db.commit()
    db.refresh(appt)
    return appt

@app.get("/appointments")
def get_appointments(db: Session = Depends(get_db)):
    return db.query(models.Appointment).all()