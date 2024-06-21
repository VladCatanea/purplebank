"use client"

import React, { useEffect, useState } from 'react'
import TransactionsTable from './TransactionsTable'
import AppNavbar from '../app/AppNavbar'
import "../app/App.css"
import { Container, List } from 'reactstrap'
import { useParams } from 'react-router-dom'

const TransactionList = () => {
    const { iban } = useParams();
    const [account, setAccount] = useState([])
	const [transactions, setTransactions] = useState([])
	const [loading, setLoading] = useState(false);

    useEffect(() => {
		setLoading(true);
		fetch(`/api/account/${iban}`, { method: 'GET' })
			.then(response => response.json())
			.then(result => {
				setAccount(result);
				setLoading(false);
			})
	}, []);

	useEffect(() => {
		setLoading(true);
		fetch(`/api/accountTransaction/${iban}`, { method: 'GET' })
			.then(response => response.json())
			.then(result => {
				setTransactions(result);
				setLoading(false);
			})
	}, []);

	if (loading) {
		return <p>Loading...</p>
	}


	return (
		<div>
			<AppNavbar />
			<Container fluid>
				<div>
					<h1>Transaction List</h1>
                    Account:
                    <List>
                        <li>IBAN : {iban}</li>
                        <li>Currency: {account.currency}</li>
                    </List>
					<TransactionsTable transactions={transactions}/>
				</div>
			</Container>
		</div>
	)
}

export default TransactionList
