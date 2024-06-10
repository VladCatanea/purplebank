"use client"

import React, { useEffect, useState } from 'react'
import AppNavbar from '../app/AppNavbar'
import "../app/App.css"
import { Button, Container, Form, Input, Label } from 'reactstrap'
import { useNavigate, useParams } from 'react-router-dom'


const EditDeposit = (props) => {
    const { id } = useParams();
    const initialFormState = { id: id, currency: "", duration: 0, interestRate: 0 }
    const [deposit, setDeposit] = useState(initialFormState)
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()

    useEffect(() => {
        if (id >= 0) {
            setLoading(true);
            fetch(`/api/deposits/${id}`, { method: 'GET' })
                .then(response => response.json())
                .then(result => {
                    setDeposit(result);
                    setLoading(false);
                })
        }
    }, []);

    if (loading) {
        return (<div>Loading ...</div>)
    }

    const handleInputChange = (event) => {
        const { name, value } = event.target

        setDeposit({ ...deposit, [name]: value })
    }

    const submitForm = async (event) => {
        event.preventDefault()
        if (id < 0) {
            await fetch(`/api/deposits`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(deposit)
            })
        }
        else {
            await fetch(`/api/deposits/${id}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(deposit)
            })
        }
        setDeposit(initialFormState)
        navigate("/deposits");
    }

    return (
        <div>
            <AppNavbar />
            <Container fluid>
                <h1>Edit Deposit</h1>
                <br />
                <Form>
                    <Label>Currency</Label>
                    <br />
                    <Input className="w-25"
                        type="text"
                        name="currency"
                        value={deposit.currency}
                        onChange={handleInputChange}
                    />
                    <br /><br />
                    <Label>Interest Rate</Label>
                    <br />
                    <Input className="w-25"
                        type="number"
                        name="interestRate"
                        value={deposit.interestRate}
                        onChange={handleInputChange}
                    />
                    <br /><br />
                    <Label>Duration (in days)</Label>
                    <br />
                    <Input className="w-25"
                        type="number"
                        name="duration"
                        value={deposit.duration}
                        onChange={handleInputChange}
                    />
                    <br /><br />
                    <Button color="primary" onClick={submitForm}>{id < 0 ? (<div>Create Deposit</div>) : (<div>Save deposit</div>)}</Button>
                </Form>
            </Container>
        </div>
    )
}

export default EditDeposit;