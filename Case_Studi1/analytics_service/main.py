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

@app.post("/analytics")
def collect(data: str, db: Session = Depends(get_db)):
    record = models.Analytics(data=data)
    db.add(record)
    db.commit()
    db.refresh(record)
    return record

@app.get("/analytics")
def report(db: Session = Depends(get_db)):
    return db.query(models.Analytics).all()