from sqlalchemy import Column, Integer, String
from database import Base

class Drug(Base):
    __tablename__ = "drugs"

    id = Column(Integer, primary_key=True)
    name = Column(String)
    stock = Column(Integer)