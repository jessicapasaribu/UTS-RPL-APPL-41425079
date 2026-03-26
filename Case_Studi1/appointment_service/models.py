from sqlalchemy import Column, Integer, String
from database import Base

class Appointment(Base):
    __tablename__ = "appointments"

    id = Column(Integer, primary_key=True)
    patient_name = Column(String)
    doctor_name = Column(String)
    date = Column(String)
    status = Column(String, default="scheduled")