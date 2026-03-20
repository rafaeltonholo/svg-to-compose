// Stub out Node.js core modules that are not available in the browser.
// These are transitively pulled in by dependencies (e.g. okio, ksoup)
// but are never actually called at runtime in the browser.
config.resolve = config.resolve || {};
config.resolve.fallback = Object.assign(config.resolve.fallback || {}, {
    "os": false,
    "path": false,
    "fs": false
});

// The svgo module is only used inside the Kobweb Worker (separate bundle).
// It leaks into the site's webpack via the project(":worker") dependency
// but is never actually called at runtime in the main thread.
config.resolve.alias = Object.assign(config.resolve.alias || {}, {
    "svgo/browser": false
});
