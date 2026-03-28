import { Link } from 'wouter';

export default function ErrorPage() {
  return (
    <div className="App-wrapper minimalist-ui flex align-items-center justify-content-center" style={{ minHeight: '80vh' }}>
      <div className="text-center">
        <h1 className="text-6xl font-bold text-navy opacity-20 mb-4">404</h1>
        <h2 className="text-2xl font-light text-slate-600 mb-6">Page Not Found</h2>
        <p className="text-slate-400 mb-8">The resource you are looking for does not exist or has been moved.</p>
        <Link href="/">
          <button className="btn-corporate-primary">
            Return to Dashboard
          </button>
        </Link>
      </div>
    </div>
  );
}
