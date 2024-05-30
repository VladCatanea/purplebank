"use client"

import React, { useState } from 'react'
import AppNavbar from './AppNavbar'
import "./App.css"
import { Button, Container, Form, Input, Label } from 'reactstrap'


const NewDeposit = () => {

    const initialFormState = { id: null, currency: "", duration: 0 }
    const [deposit, setDeposit] = useState(initialFormState)

    const handleInputChange = (event) => {
        const { name, value } = event.target

        setDeposit({ ...deposit, [name]: value })
    }

    const submitForm = async (event) => {
        event.preventDefault()

        await fetch(`/api/deposits`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(deposit)
        })
        setDeposit(initialFormState)
        window.location.href = "/deposits";
    }

    return (
        <div>
            <AppNavbar />
            <Container fluid>
                <h1>New Deposit</h1>
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
                    <Label>Duration (in days)</Label>
                    <br />
                    <Input className="w-25"
                        type="number"
                        name="duration"
                        value={deposit.duration}
                        onChange={handleInputChange}
                    />
                    <br /><br />
                    <Button color="primary" onClick={submitForm}>Add new deposit</Button>
                </Form>
            </Container>
        </div>
    )
}

export default NewDeposit;