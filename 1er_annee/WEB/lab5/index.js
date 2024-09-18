const LOCAL_STORAGE_KEY = "FORM_SUBMIT_VALUES";
const MANDATORY_INPUT_NAMES = ["pseudo", "password", "passwordConfirm"];
const SUCESS_TITLE_STYLE = "color: #38c172; opacity: 0; font-size: 1.2rem; font-family: Arial;";
const ERROR_TITLE_STYLE = "color: #e3342f; opacity: 0.8; font-size: 1.2rem; font-family: Arial;";
const BASE_TITLE_STYLE = "color: #777; opacity: 0.8; font-size: 1.2rem; font-family: Arial;";
const UL_STYLE = "margin: 0; font-family: Arial;";
const LI_STYLE =
    "margin: 0.5rem 0; padding: 0.5rem 0; border-bottom: 1px solid #ccc; font-family: Arial;";
const INPUT_VALIDATIONS = {
    pseudo: {
        check: (i) => length(i) > 2 && length(i) < 10,
        errorMessage: "Le pseudo doit contenir entre 3 et 10 caractÃ¨res",
    },
    birthdate: {
        check: (i) => i.max === "2024-04-10",
        errorMessage: "La date de naissance doit Ãªtre infÃ©rieure Ã  aujourd'hui",
    },
};

function saveParameters(form) {
    const parameters = { method: form.method };
    for (const input of form.querySelectorAll("input")) {
        if (input.type !== "submit") {
            parameters[input.name] = {
                value: input.value,
                min: input.min,
                max: input.max,
            };
        }
    }
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(parameters));
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(parameters));
}

function generateList(arr) {
    const ul = document.createElement("ul");
    ul.style.cssText = UL_STYLE;
    for (const item of arr) {
        const li = document.createElement("li");
        li.textContent = item;
        li.style.cssFloat = LI_STYLE;
        ul.appendChild(li);
    }
    return ul;
}

function generateTitle(content, { cssText = BASE_TITLE_STYLE } = {}) {
    const element = document.createElement("h1");
    element.textContent = content;
    if (cssText) {
        element.style.cssText = cssText;
    }
    return element;
}

function validateParameters() {
    const parameters = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY) ?? "false");
    if (!parameters) {
        return;
    }
    localStorage.removeItem(LOCAL_STORAGE_KEY);
    const reportElement = document.createElement("div");
    document.body.appendChild(reportElement);
    if (parameters.method !== "post") {
        reportElement.appendChild(
            generateTitle("Mauvaise mÃ©thode utilisÃ©e, utilisez la methode 'post'", {
                cssText: ERROR_TITLE_STYLE,
            })
        );
        return;
    }
    delete parameters.method;
    const filledParameters = Object.fromEntries(
        Object.entries(parameters).filter(([_, { value }]) => Boolean(value))
    );
    if (Object.keys(filledParameters).length === 0) {
        reportElement.appendChild(
            generateTitle("Aucune valeur envoyÃ©e", { cssText: ERROR_TITLE_STYLE })
        );
        return;
    }
    const missingInputs = MANDATORY_INPUT_NAMES.filter((name) => !filledParameters[name]);
    reportElement.appendChild(generateTitle("Valeurs envoyÃ©es:"));
    reportElement.appendChild(
        generateList(Object.keys(filledParameters).map((key) => `${key}: ${parameters[key].value}`))
    );
    if (missingInputs.length) {
        reportElement.appendChild(
            generateTitle("Valeurs manquantes:", { cssText: ERROR_TITLE_STYLE })
        );
        reportElement.appendChild(generateList(missingInputs));
    }
    console.log(parameters);
    const invalidInputs = Object.keys(parameters).filter(
        (key) => key in INPUT_VALIDATIONS && !INPUT_VALIDATIONS[key].check(parameters[key])
    );
    if (invalidInputs.length > 0) {
        reportElement.appendChild(
            generateTitle("DÃ©finitions invalides:", { cssText: ERROR_TITLE_STYLE })
        );
        reportElement.appendChild(
            generateList(
                invalidInputs.map((name) => `${name}: ${INPUT_VALIDATIONS[name].errorMessage}`)
            )
        );
    }
}





document.addEventListener(
    "DOMContentLoaded",
    () => {
        const form = document?.querySelector("form");
        form?.querySelector("#birthdate")?.max = new Date().toISOString().split('T')[0];
        form.addEventListener("submit", () => saveParameters(form), { once: true });
        validateParameters();
    },
    { once: true }
);