"use client"

import React, { useEffect, useState } from 'react'
import AppNavbar from './AppNavbar'
import "./App.css"
import { Button, Container, Form, Input, Label } from 'reactstrap'
import { useParams } from 'react-router-dom'


const EditDeposit = () => {

    const { id } = useParams();
    const initialFormState = { id: id, currency: "", duration: 0 }
    const [deposit, setDeposit] = useState(initialFormState)
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

    if (loading){
        return (<div>Loading ...</div>)
    }

    const handleInputChange = (event) => {
        const { name, value } = event.target

        setDeposit({ ...deposit, [name]: value })
    }

    const submitForm = async (event) => {
        event.preventDefault()

        await fetch(`/api/deposits/${id}`, {
            method: 'PUT',
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
                    <Label>Duration (in days)</Label>
                    <br />
                    <Input className="w-25"
                        type="number"
                        name="duration"
                        value={deposit.duration}
                        onChange={handleInputChange}
                    />
                    <br /><br />
                    <Button color="primary" onClick={submitForm}>Edit deposit</Button>
                </Form>
            </Container>
        </div>
    )
}

export default EditDeposit;