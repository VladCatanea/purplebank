import React from 'react'
import { Table } from 'reactstrap'
import { ROLE_ADMIN } from "./Constants"

const SavingsTable = (props) => (
	<Table className="mt-4">
		<thead>
			<tr>
				<th width="20%">Id</th>
				<th width="20%">depositId</th>
				<th width="20%">Amount</th>
				{/* {props.permission === ROLE_ADMIN ? (<th width="20%"><div align="left">Actions</div></th>) : (<th width="20%"></th>)} */}
			</tr>
		</thead>
		<tbody>
			{props.savings.map((saving) => (
				<tr key={saving.id}>
					<td>{saving.id}</td>
                    <td>{saving.depositId}</td>
					<td>{saving.amount}</td>
					{/* {props.permission === ROLE_ADMIN ? (

						<td>
							<button className="button edit" size="sm" onClick={() => window.location.href = `/savings/edit/${Savings.id}`}>Edit Savings</button>
							<button className="button delete" size="sm" onClick={() => props.remove(Savings.id)}>Delete Savings</button>
						</td>) : (<td>
							<button className="button edit" size="sm" onClick={() => window.location.href = `/savings/create/${Savings.id}`}>Create savings</button>
						</td>)} */}
				</tr>
			))}
		</tbody>
	</Table>
)

export default SavingsTable