"use client"

import React, { useEffect, useState } from 'react'
import AppNavbar from './AppNavbar'
import "./App.css"
import { Button, Container, Form, Input, Label } from 'reactstrap'
import { useParams } from 'react-router-dom'


const CreateSavings = () => {

    const { id } = useParams();
    const initialSavingsState = { id: null, depositId: id, amount: 0, owner: null, expiration: null }
    const initialDepositState = { id: id, currency: "", duration: 0 }
    const [savings, setSavings] = useState(initialSavingsState)
    const [deposit, setDeposit] = useState(initialDepositState)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        setLoading(true);
        fetch(`/api/deposits/${id}`, { method: 'GET' })
            .then(response => response.json())
            .then(result => {
                setDeposit(result);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return (<div>Loading ...</div>)
    }

    const handleInputChange = (event) => {
        const { name, value } = event.target

        setSavings({ ...savings, [name]: value })
    }

    const submitForm = async (event) => {
        event.preventDefault()

        await fetch(`/api/savings`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(savings)
        })
        setSavings(initialSavingsState)
        window.location.href = "/savings";
    }

    return (
        <div>
            <AppNavbar />
            <Container fluid>
                <h1>Create Savings</h1>
                <br />
                <Form>
                    <Label>Currency</Label>
                    <br />
                    <Input className="w-25"
                        type="text"
                        name="currency"
                        value={deposit.currency}
                    />
                    <br /><br />
                    <Label>Duration (in days)</Label>
                    <br />
                    <Input className="w-25"
                        type="text"
                        name="duration"
                        value={deposit.duration}
                    />
                    <br /><br />
                    <Label>Amount</Label>
                    <br />
                    <Input className="w-25"
                        type="text"
                        name="amount"
                        value={savings.amount}
                        onChange={handleInputChange}
                    />
                    <br /><br />
                    <Button color="primary" onClick={submitForm}>Create Savings</Button>
                </Form>
            </Container>
        </div>
    )
}

export default CreateSavings;