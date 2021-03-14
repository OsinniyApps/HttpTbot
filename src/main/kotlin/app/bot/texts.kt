package app.bot

val TEXT_START = """
    Welcome to *HTTP BOT*!

    With this bot you can proceed any http requests and get responses

    /help - how to use

    Bot also supports inline mode
""".trimIndent()

val TEXT_HELP = """
    *Usage:*

    _/<method> <url> <content type>? <body>?_
    ? - optional argument

    Inline signature: _<method> <url> <content type>? <body>?_

    Available methods: _get_, _post_, _put_, _patch_, _head_, _delete_
    Url must be in format <http/https>://<path>
""".trimIndent()

const val TEXT_INLINE_SHORT_RESPONSE = "Short response"

const val TEXT_INLINE_FULL_RESPONSE = "Full response"

const val TEXT_ERROR = "Error"

const val ERROR_REQUEST = "An error occurred while proceeding your request"

const val ERROR_INVALID_METHOD = "Invalid method"

const val ERROR_NO_URL = "No url"

const val ERROR_URL_NOT_VALID = "Not valid url"

const val ERROR_INVALID_CONTENT_TYPE = "Invalid content type"


val httpStatuses = hashMapOf(
    100 to "Continue",
    101 to "Switching Protocols",
    102 to "Processing",
    103 to "Early Hints",

    200 to "OK",
    201 to "Created",
    202 to "Accepted",
    203 to "Non-Authoritative Information",
    204 to "No Content",
    205 to "Reset Content",
    206 to "Partial Content",
    207 to "Multi-Status",
    208 to "Already Reported",
    226 to "IM Used",

    300 to "Multiple Choices",
    301 to "Moved Permanently",
    302 to "Moved Temporarily",
    303 to "See Other",
    304 to "Not Modified",
    305 to "Use Proxy",
    306 to "Switch Proxy",
    307 to "Temporary Redirect",
    308 to "Permanent Redirect",

    400 to "Bad Request",
    401 to "Unauthorized",
    402 to "Payment Required",
    403 to "Forbidden",
    404 to "Not Found",
    405 to "Method Not Allowed",
    406 to "Not Acceptable",
    407 to "Proxy Authentication Required",
    408 to "Request Timeout",
    409 to "Conflict",
    410 to "Gone",
    411 to "Length Required",
    412 to "Precondition Failed",
    413 to "Payload Too Large",
    414 to "URI Too Long",
    415 to "Unsupported Media Type",
    416 to "Range Not Satisfiable",
    417 to "Expectation Failed",
    418 to "I’m a teapot",
    419 to "Authentication Timeout",
    421 to "Misdirected Request",
    422 to "Unprocessable Entity",
    423 to "Locked",
    424 to "Failed Dependency",
    425 to "Too Early",
    426 to "Upgrade Required",
    428 to "Precondition Required",
    429 to "Too Many Requests",
    431 to "Request Header Fields Too Large",
    434 to "Requested host unavailable",
    449 to "Retry With",
    451 to "Unavailable For Legal Reasons",
    499 to "Client Closed Request",

    500 to "Internal Server Error",
    501 to "Not Implemented",
    502 to "Bad Gateway",
    503 to "Service Unavailable",
    504 to "Gateway Timeout",
    505 to "HTTP Version Not Supported",
    506 to "Variant Also Negotiates",
    507 to "Insufficient Storage",
    508 to "Loop Detected",
    509 to "Bandwidth Limit Exceeded",
    510 to "Not Extended",
    511 to "Network Authentication Required",
    520 to "Unknown Error",
    521 to "Web Server Is Down",
    522 to "Connection Timed Out",
    523 to "Origin Is Unreachable",
    524 to "A Timeout Occurred",
    525 to "SSL Handshake Failed",
    526 to "Invalid SSL Certificate"
)
