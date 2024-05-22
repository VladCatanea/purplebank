"use client"

import React, { useEffect, useLayoutEffect, useState } from 'react'
import DepositTable from './DepositTable'
import AppNavbar from './AppNavbar'
import "./App.css"

const DepositList = () => {
	const initialDeposits = []

	const [deposits, setDeposits] = useState([])
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		setLoading(true);
		fetch('/api/deposits', {method: 'GET'})
			.then(response => response.json())
			.then(result => {
				setDeposits(result);
				setLoading(false);
			})
	}, []); 

	if (loading) {
		return <p>Loading...</p>
	} 



	return (
		<div>
      <AppNavbar/>
			<div className="container">
				<h1>CRUD App with Hooks</h1>
				<DepositTable deposits={deposits} />
			</div>
		</div>
	)
}

export default DepositList
