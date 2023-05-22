<aside class="sidebar d-none d-sm-flex">
    <nav class="d-flex flex-column mb-4">
        <a class="d-inline-flex mb-12 justify-content-center w-100 rst-btn mb-5" href="/">
            <img src="static/img/logo.png" height="52" alt="logo">
        </a>
        <a
                href="/users"
                class="d-inline-flex mb-4 justify-content-center w-100 rst-btn"
        >
            <i class="fas fa-solid fa-users"></i>
        </a>
        <a
                href="/liked"
                class="d-inline-flex mb-4 justify-content-center w-100 rst-btn"
        >
            <i class="fas fa-solid fa-heart"></i>
        </a>
        <a
                href="/messages"
                class="d-inline-flex mb-4 justify-content-center w-100 rst-btn"
        >
            <i class="fas fa-solid fa-comments"></i>
        </a>
    </nav>
    <form action="/logout" method="POST">
        <button class="rst-btn d-flex justify-content-center w-100 align-content-center">
            <i class="fas fa-sign-out-alt"></i>
        </button>
    </form>
</aside>