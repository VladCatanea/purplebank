import React from 'react'
import { Link } from 'react-router-dom'
import { Button, Table } from 'reactstrap'

const DepositTable = (props) => (
	<Table className="mt-4">
		<thead>
			<tr>
				<th width="20%">Id</th>
				<th width="20%">Currency</th>
				<th width="20%">Duration</th>
				{props.permission === "ADMIN" ? (<th width="20%">Actions</th>) : (<th width="20%"></th>)}
			</tr>
		</thead>
		<tbody>
			{props.deposits.map((deposit) => (
				<tr key={deposit.id}>
					<td>{deposit.id}</td>
					<td>{deposit.currency}</td>
					<td>{deposit.duration}</td>
					{props.permission === "ADMIN" ? (

						<td>
							<Button size="sm" color="primary" tag={Link} to={"/deposits/edit/" + deposit.id}>Edit deposit</Button>
							<Button size="sm" color="danger" onClick={() => props.remove(deposit.id)}>Delete deposit</Button>
						</td>) : (<td></td>)}
				</tr>
			))}
		</tbody>
	</Table>
)

export default DepositTable