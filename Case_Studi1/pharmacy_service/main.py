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

@app.post("/drugs")
def add_drug(name: str, stock: int, db: Session = Depends(get_db)):
    drug = models.Drug(name=name, stock=stock)
    db.add(drug)
    db.commit()
    db.refresh(drug)
    return drug

@app.get("/drugs")
def get_drugs(db: Session = Depends(get_db)):
    return db.query(models.Drug).all()