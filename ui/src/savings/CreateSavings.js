"use client"

import React, { useEffect, useState } from 'react'
import AppNavbar from '../app/AppNavbar'
import "../app/App.css"
import { Button, Container, Form, Input, Label, ButtonDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap'
import { useNavigate, useParams } from 'react-router-dom'


const CreateSavings = () => {

    const { id } = useParams();
    const initialSavingsState = { id: null, depositId: id, amount: 0, owner: null, expiration: null }
    const initialDepositState = { id: id, currency: "", duration: 0 }
    const [savings, setSavings] = useState(initialSavingsState)
    const [deposit, setDeposit] = useState(initialDepositState)
    const [loading, setLoading] = useState(false)
    const [dropdownState, setDropdownState] = useState(false)
    const [iban, setIban] = useState('Select account')
    const [accounts, setAccounts] = useState([])
    const navigate = useNavigate()
    const [message, setMessage] = useState("")
    const [msg2, setMsg2] = useState("")

    useEffect(() => {
        setLoading(true);
        fetch(`/api/deposits/${id}`, { method: 'GET' })
            .then(response => response.json())
            .then(result => {
                setDeposit(result);
                setLoading(false);
            })
    }, []);

    useEffect(() => {
        setLoading(true);
        fetch('/api/account', { method: 'GET' })
            .then(response => response.json())
            .then(result => {
                setAccounts(result)
                setLoading(false)
            })
    }, [])

    if (loading) {
        return (<div>Loading ...</div>)
    }

    const handleInputChange = (event) => {
        const { name, value } = event.target

        setSavings({ ...savings, [name]: value })
    }

    const submitForm = async (event) => {
        event.preventDefault()

        await fetch(`/api/savings?iban=${iban}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(savings)
        }).then(res => {
            if (!res.ok) {
                res.text().then(text => { setMessage(text) })
            }
            else {
                setSavings(initialSavingsState)
                navigate("/savings");
            }
        })
        setMsg2("Form contains errors, please correct them and retry:")
    }



const toggle = () => {
    setDropdownState(!dropdownState)
}

const changeValue = (e) => {
    setIban(e.currentTarget.textContent)
}

return (
    <div>
        <AppNavbar />
        <Container fluid>
            <h1>Create Savings</h1>
            <br />
            {msg2} <br />
            <div style={{ color: "red" }}>{message}</div>
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

                <Label>Account to be credited</Label>
                <br />
                <ButtonDropdown className="w-25" isOpen={dropdownState} toggle={toggle}>
                    <DropdownToggle caret>
                        {iban}
                    </DropdownToggle>
                    <DropdownMenu>
                        {accounts.map(account =>
                            account.currency === deposit.currency ?
                                <DropdownItem onClick={changeValue}>{account.iban} ({account.amount})</DropdownItem> :
                                null
                        )}

                    </DropdownMenu>
                </ButtonDropdown>

                <br /><br />

                <Button color="primary" onClick={submitForm}>Create Savings</Button>
            </Form>
        </Container>
    </div>
)
}

export default CreateSavings;