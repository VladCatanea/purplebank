"use client"

import React, { useEffect, useState } from 'react'
import DepositTable from './DepositTable'
import AppNavbar from './AppNavbar'
import "./App.css"
import { Button, Container } from 'reactstrap'
import { Link } from 'react-router-dom'

const DepositList = () => {
	const [deposits, setDeposits] = useState([])
	const [loading, setLoading] = useState(false);
	const [permission, setPermission] = useState("");

	useEffect(() => {
		setLoading(true);
		fetch('/api/deposits', { method: 'GET' })
			.then(response => response.json())
			.then(result => {
				setDeposits(result);
				setLoading(false);
			})
	}, []);

	useEffect(() => {
		setLoading(true);
		fetch('/api/permission', { method: 'GET' })
			.then(response => response.json())
			.then(result => {
				setPermission(result.permission);
				setLoading(false);
			})
	}, []);

	const remove = async (id) => {
		await fetch(`/api/deposits/${id}`, {
			method: 'DELETE',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			}
		}).then(() => {
			fetch('/api/deposits', { method: 'GET' })
				.then(response => response.json())
				.then(result => {
					setDeposits(result);
				})
		});
	}

	if (loading) {
		return <p>Loading...</p>
	}


	return (
		<div>
			<AppNavbar />
			<Container fluid>
				<div>
					{
						permission === "ADMIN" ? (<div className="float-end">
							<Button color="success" tag={Link} to="/deposits/new">Add Deposit</Button> </div>)
							: (<div></div>)
					}
					<h1>Deposit List</h1>
					<DepositTable deposits={deposits} permission={permission} remove={remove} />
				</div>
			</Container>
		</div>
	)
}

export default DepositList
