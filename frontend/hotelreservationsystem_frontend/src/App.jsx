import AppRoutes from './AppRoutes'
import Header from './components/Header/Header'
import { AuthProvider } from './hooks/AuthContext'

function App() {
  return (
    <AuthProvider>
      <Header />
      <AppRoutes />
    </AuthProvider>

  )
}

export default App
