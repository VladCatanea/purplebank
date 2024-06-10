import React from 'react'
import { Table } from 'reactstrap'
import { ROLE_ADMIN } from "../app/Constants"
import { useNavigate } from 'react-router-dom'

const DepositTable = (props) => {
	const navigate = useNavigate()
	return(
	<Table className="mt-4">
		<thead>
			<tr>
				<th width="10%">Id</th>
				<th width="15%">Currency</th>
				<th width="15%"> Interest rate</th>
				<th width="15%">Duration</th>
				<th width="20%"><div align="left">Actions</div></th>
			</tr>
		</thead>
		<tbody>
			{props.deposits.map((deposit) => (
				<tr key={deposit.id}>
					<td>{deposit.id}</td>
					<td>{deposit.currency}</td>
					<td>{deposit.interestRate}%</td>
					<td>{deposit.duration}</td>
					{props.permission === ROLE_ADMIN ? (

						<td>
							<button className="button edit" size="sm" onClick={() => navigate(`/deposits/edit/${deposit.id}`)}>Edit</button>
							<button className="button delete" size="sm" onClick={() => props.remove(deposit.id)}>Delete</button>
						</td>) : (<td>
							<button className="button edit" size="sm" onClick={() => navigate(`/savings/create/${deposit.id}`)}>Create savings</button>
						</td>)}
				</tr>
			))}
		</tbody>
	</Table>
)
}
export default DepositTable