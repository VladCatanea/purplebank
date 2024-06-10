"use client"

import React, { useEffect, useState } from 'react'
import DepositTable from './DepositTable'
import AppNavbar from '../app/AppNavbar'
import "../app/App.css"
import { Container } from 'reactstrap'
import { ROLE_ADMIN } from "../app/Constants"
import { useNavigate } from 'react-router-dom'

const DepositList = () => {
	const [deposits, setDeposits] = useState([])
	const [loading, setLoading] = useState(false);
	const [permission, setPermission] = useState("");
	const navigate = useNavigate()

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
						permission === ROLE_ADMIN ? (<div className="float-end">
							<button className="button create" onClick={() => navigate("/deposits/edit/-1")}>Add Deposit</button> </div>)
							: (null)
					}
					<h1>Deposit List</h1>
					<DepositTable deposits={deposits} permission={permission} remove={remove} />
				</div>
			</Container>
		</div>
	)
}

export default DepositList
