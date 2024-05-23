"use client"

import React, { useEffect, useState } from 'react'
import DepositTable from './DepositTable'
import AppNavbar from './AppNavbar'
import "./App.css"
import { Container } from 'reactstrap'

const DepositList = () => {
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
	  <Container fluid>
			<div>
				<h1>Deposit List</h1>
				<DepositTable deposits={deposits} />
			</div>
			</Container>
		</div>
	)
}

export default DepositList
