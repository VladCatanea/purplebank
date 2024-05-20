"use client"

import React, { useState } from 'react'
import DepositTable from './DepositTable'

const App = () => {
	const initialDeposits = []

	const [deposits, setDeposits] = useState(false)

    const username = 'user';
	const password = 'password';
	let headers = new Headers();

	headers.set('Authorization', 'Basic ' + Buffer.from(username + ":" + password).toString('base64'));
	headers.set('Accept', 'application/json')

  fetch('/api/deposits', {method: 'GET', headers: headers})
  .then(response => response.json())
  .then(data => {
	setDeposits(data);})
	
	return (
		<div>
			{deposits}
		</div>
	)

	return (
	<div className="container">
		<h1>CRUD App with Hooks</h1>
			<DepositTable deposits={deposits} />
	</div>
  )
}

export default App