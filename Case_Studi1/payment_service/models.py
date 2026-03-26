from sqlalchemy import Column, Integer, String
from database import Base

class Payment(Base):
    __tablename__ = "payments"

    id = Column(Integer, primary_key=True)
    patient_name = Column(String)
    amount = Column(Integer)
    status = Column(String, default="pending")