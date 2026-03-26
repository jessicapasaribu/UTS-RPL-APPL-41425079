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

@app.post("/ehr")
def create_ehr(patient_name: str, history: str, prescription: str, db: Session = Depends(get_db)):
    ehr = models.EHR(
        patient_name=patient_name,
        history=history,
        prescription=prescription
    )
    db.add(ehr)
    db.commit()
    db.refresh(ehr)
    return ehr

@app.get("/ehr")
def get_ehr(db: Session = Depends(get_db)):
    return db.query(models.EHR).all()