import React, { useEffect, useState } from 'react'
import { Button, Container, Form, Input, Label } from 'reactstrap';
import AppNavBar from '../app/AppNavbar'
import { ROLE_ADMIN } from '../app/Constants';
import TransactionHistoryTable from './TransactionHistoryTable'


const TransactionHistory = () => {
    const [permission, setPermission] = useState()
    const [transactions, setTransactions] = useState([])

    useEffect(() => {
        fetch("/api/permission", { method: "GET" })
            .then(response => response.json())
            .then(result => {
                setPermission(result.permission)
            })
    }, [])

    useEffect(() => {
        fetch("/api/transactions", { method: "GET" })
            .then(response => response.json())
            .then(result => {
                setTransactions(result)
            })
    }, [])

    if (permission !== ROLE_ADMIN) {
        return (<div>
            <AppNavBar />
            <div>Only admin user allowed on this page</div>
        </div>)
    }

    return (
        <div>
            <AppNavBar />
            <Container>
                <h1>Transaction History</h1>
                <TransactionHistoryTable transactions={transactions} />
            </Container>
        </div>
    );

}

export default TransactionHistory