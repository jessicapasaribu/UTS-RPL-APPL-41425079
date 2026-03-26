from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import httpx

app = FastAPI()

# SERVICE URL
USER_SERVICE = "http://localhost:8001"
APPOINTMENT_SERVICE = "http://localhost:8002"
EHR_SERVICE = "http://localhost:8003"
PHARMACY_SERVICE = "http://localhost:8004"
PAYMENT_SERVICE = "http://localhost:8005"

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ---------------- USER ----------------
@app.get("/users")
async def get_users():
    async with httpx.AsyncClient() as client:
        res = await client.get(f"{USER_SERVICE}/users")
        return res.json()

@app.post("/users")
async def create_user(user: dict):
    async with httpx.AsyncClient() as client:
        res = await client.post(f"{USER_SERVICE}/users", json=user)
        return res.json()

# ---------------- APPOINTMENT ----------------
@app.get("/appointments")
async def get_appointments():
    async with httpx.AsyncClient() as client:
        res = await client.get(f"{APPOINTMENT_SERVICE}/appointments")
        return res.json()

@app.post("/appointments")
async def create_appointment(data: dict):
    async with httpx.AsyncClient() as client:
        res = await client.post(f"{APPOINTMENT_SERVICE}/appointments", json=data)
        return res.json()

# ---------------- EHR ----------------
@app.get("/ehr")
async def get_ehr():
    async with httpx.AsyncClient() as client:
        res = await client.get(f"{EHR_SERVICE}/ehr")
        return res.json()

@app.post("/ehr")
async def create_ehr(data: dict):
    async with httpx.AsyncClient() as client:
        res = await client.post(f"{EHR_SERVICE}/ehr", json=data)
        return res.json()

# ---------------- PHARMACY ----------------
@app.get("/drugs")
async def get_drugs():
    async with httpx.AsyncClient() as client:
        res = await client.get(f"{PHARMACY_SERVICE}/drugs")
        return res.json()

@app.post("/drugs")
async def create_drug(data: dict):
    async with httpx.AsyncClient() as client:
        res = await client.post(f"{PHARMACY_SERVICE}/drugs", json=data)
        return res.json()

# ---------------- PAYMENT ----------------
@app.get("/payments")
async def get_payments():
    async with httpx.AsyncClient() as client:
        res = await client.get(f"{PAYMENT_SERVICE}/payments")
        return res.json()

@app.post("/payments")
async def create_payment(data: dict):
    async with httpx.AsyncClient() as client:
        res = await client.post(f"{PAYMENT_SERVICE}/payments", json=data)
        return res.json()

# ROOT
@app.get("/")
def root():
    return {"message": "API Gateway Running "}