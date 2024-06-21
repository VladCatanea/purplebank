"use client"

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import "./App.css"
import Home from "./Home"
import DepositList from "../deposit/DepositList"
import EditDeposit from "../deposit/EditDeposit"
import CreateSavings from "../savings/CreateSavings"
import SavingsList from "../savings/SavingsList"
import AccountsList from "../account/AccountList"
import TransactionUpload from "../transactions/TransactionUpload"
import TransactionHistory from "../transactions/TransactionHistory"
import TransactionList from "../account/TransactionList"

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home/>}/>
        <Route path='/deposits' exact={true} element={<DepositList/>}/>
        <Route path='/deposits/edit/:id' element={<EditDeposit/>}/>
        <Route path='/savings/create/:id' element={<CreateSavings/>}/>
        <Route path='/savings' exact={true} element={<SavingsList/>}/>
        <Route path='/accounts' exact={true} element={<AccountsList/>}/>
        <Route path='/transactions' exact={true} element={<TransactionUpload/>}/>
        <Route path='/transaction/history' exact={true} element={<TransactionHistory/>}/>
        <Route path='/account/:iban' element={<TransactionList/>}/>
      </Routes>
    </Router>
  )
}

export default App
