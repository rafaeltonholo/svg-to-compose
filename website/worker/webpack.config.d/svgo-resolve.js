// Add "import" condition to resolve svgo/browser export
config.resolve = config.resolve || {};
config.resolve.conditionNames = config.resolve.conditionNames || [];
if (!config.resolve.conditionNames.includes("import")) {
    config.resolve.conditionNames.push("import");
}
