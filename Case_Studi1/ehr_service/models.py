from sqlalchemy import Column, Integer, String
from database import Base

class EHR(Base):
    __tablename__ = "ehr"

    id = Column(Integer, primary_key=True)
    patient_name = Column(String)
    history = Column(String)
    prescription = Column(String)