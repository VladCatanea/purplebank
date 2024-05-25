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
				{props.permission == "ADMIN" ? (<th width="20%">Actions</th>) : (<th width="20%"></th>)}
			</tr>
		</thead>
		<tbody>
			{props.deposits.map((deposit) => (
				<tr key={deposit.id}>
					<td>{deposit.id}</td>
					<td>{deposit.currency}</td>
					<td>{deposit.duration}</td>
					{props.permission == "ADMIN" ? (

						<td>
							<Button color="success" tag={Link} to="/groups/edit">Edit deposit</Button>
							<Button color="success" tag={Link} to="/groups/delete">Delete deposit</Button>
						</td>) : (<td></td>)}
				</tr>
			))}
		</tbody>
	</Table>
)

export default DepositTable