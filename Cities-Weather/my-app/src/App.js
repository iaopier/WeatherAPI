import React, {Component} from 'react';
import { BrowserRouter, Route ,Switch , Link } from "react-router-dom";

import City from "./components/City"
import Cities from "./components/Cities"
import Error from "./components/Error"

class App extends Component{

  render(){
      return (
        <BrowserRouter>
          <Switch>
            <Route path="/" component={Cities} exact/>
            <Route path="/city/:country/:name" component={City}/>
            <Route component={Error}/>
          </Switch>
        </BrowserRouter>
      )
  }
}
export default App;
