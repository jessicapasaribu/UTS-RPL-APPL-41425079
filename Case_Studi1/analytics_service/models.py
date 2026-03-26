from sqlalchemy import Column, Integer, String
from database import Base

class Analytics(Base):
    __tablename__ = "analytics"

    id = Column(Integer, primary_key=True)
    data = Column(String)