"use client"

import React, { useEffect, useState } from 'react'
import DepositTable from './DepositTable'

const App = () => {
	const initialDeposits = []

	const [deposits, setDeposits] = useState([])
	const [loading, setLoading] = useState(false);

	const username = 'user';
	const password = 'password';
	let headers = new Headers();

	headers.set("Authorization", 'Basic ' + Buffer.from(username + ":" + password).toString('base64'));

	useEffect(() => {
		setLoading(true);
		fetch('/api/deposits', { method: 'GET', headers: headers })
			.then(response => response.json())
			.then(result => { 
				setDeposits(result); 
				setLoading(false);
			})
	}, []);

	if(loading){
		return <p>Loading...</p>
	}

	/**fetch('http://localhost:8080/api/deposits', { method: 'GET', headers: headers })
		.then(response => response.json())
		.then(data => {
			setDeposits(data);
		})**/

	/**return (
		<div>{deposits.map((deposit) => (<div>{deposit.id}</div>))}</div>
	)**/

	return (
		<div className="container">
			<h1>CRUD App with Hooks</h1>
			<DepositTable deposits={deposits} />
		</div>
	)
}

export default App