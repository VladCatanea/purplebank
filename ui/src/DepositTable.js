import React from 'react'
import { Table } from 'reactstrap'

const DepositTable = (props) => (
	<Table className="mt-4">
		<thead>
			<tr>
				<th width="20%">Id</th>
				<th width="20%">Currency</th>
				<th width="20%">Duration</th>
			</tr>
		</thead>
		<tbody>
			{props.deposits.map((deposit) => (
				<tr key={deposit.id}>
					<td>{deposit.id}</td>
					<td>{deposit.currency}</td>
					<td>{deposit.duration}</td>
				</tr>
			))}
		</tbody>
	</Table>
)

export default DepositTable