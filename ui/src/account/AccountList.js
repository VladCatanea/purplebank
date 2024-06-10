"use client"

import React, { useEffect, useState } from 'react'
import AccountsTable from './AccountTable'
import AppNavbar from '../app/AppNavbar'
import "../app/App.css"
import { Container } from 'reactstrap'

const AccountsList = () => {
	const [accounts, setAccounts] = useState([])
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		setLoading(true);
		fetch('/api/account', { method: 'GET' })
			.then(response => response.json())
			.then(result => {
				setAccounts(result);
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
					<h1>Account List</h1>
					<AccountsTable accounts={accounts}/>
				</div>
			</Container>
		</div>
	)
}

export default AccountsList
