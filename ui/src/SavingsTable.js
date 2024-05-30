import React from 'react'
import { Table } from 'reactstrap'

const SavingsTable = (props) => (
	<Table className="mt-4">
		<thead>
			<tr>
				<th width="20%">Id</th>
				<th width="20%">depositId</th>
				<th width="20%">Amount</th>
				<th width="20%"><div align="left">Actions</div></th>
			</tr>
		</thead>
		<tbody>
			{props.savings.map((saving) => (
				<tr key={saving.id}>
					<td>{saving.id}</td>
                    <td>{saving.depositId}</td>
					<td>{saving.amount}</td>
						<td>
							{/* <button className="button edit" size="sm" onClick={() => window.location.href = `/savings/edit/${saving.id}`}>Edit Savings</button> */}
							<button className="button delete" size="sm" onClick={() => props.remove(saving.id)}>Delete Savings</button>
						</td>
				</tr>
			))}
		</tbody>
	</Table>
)

export default SavingsTable