<form class="form-signin" method="post">
    <form class="form-signin" action="/register" method="post">
        <h1 class="h3 mb-3 font-weight-normal">Registration of a new account</h1>
        <div class="row mb2">
            <div class="col-12 col-lg-6">
                <label for="firstName" class="sr-only">First name</label>
                <input
                        type="text" id="firstName" name="firstName" class="form-control"
                        value="Sean"
                        placeholder="First name"
                        required
                >
            </div>

            <div class="col-12 col-lg-6">
                <label for="lastName" class="sr-only">Last name</label>
                <input
                        type="text" id="lastName" name="lastName" class="form-control"
                        placeholder="Last name"
                        value="Rad"
                        required
                >
            </div>
        </div>


        <label for="profession" class="sr-only">Profession</label>
        <input value="Tinder SEO" type="text" id="profession" name="profession" class="form-control"
               placeholder="Profession" required>

        <label for="inputEmail" class="sr-only">Email address</label>
        <input
                type="email" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email address"
                required
                autofocus
        >
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        <label for="repeatPassword" class="sr-only">Repeat password</label>
        <input type="password" id="repeatPassword" name="repeatPassword" class="form-control"
               placeholder="Repeat password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
        <#include "../copyright.ftl">
    </form>
</form>