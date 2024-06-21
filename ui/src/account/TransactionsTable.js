import React from 'react'
import { Table } from 'reactstrap'

const TransactionsTable = (props) => (
    <Table className="mt-4">
        <thead>
            <tr>
                <th>Reference Number</th>
                <th>Sender Iban</th>
                <th>Sender Name</th>
                <th>Debit Amount</th>
                <th>Credit Amount</th>
                <th>Transaction Date</th>

            </tr>
        </thead>
        <tbody>
            {props.transactions.map((transaction) => 
                (<tr>
                    <td>{transaction.referenceNum}</td>
                    <td>{transaction.senderIban}</td>
                    <td>{transaction.senderName}</td>
                    <td>{transaction.debitAmount}</td>
                    <td>{transaction.creditAmount}</td>
                    <td>{transaction.transactionDate.split('T')[0] + ', ' + transaction.transactionDate.split('T')[1].split('.')[0]}</td>
                </tr>)
            )}
        </tbody>
    </Table>
)


export default TransactionsTable