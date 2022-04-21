<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

</main>

<c:import url="/WEB-INF/jsp/common/waitingModal.jsp"></c:import>
<a href="#navbar" id="toTop"><i class="bi bi-arrow-up"></i></a>

<div id="footer" class="container-fluid mt-auto px-5">
  <footer class="py-5">
    <div class="row g-0">
      <div class="col-6">
        <h5>Contact</h5>
        <ul class="nav flex-column">
          <li class="nav-item mb-2"><a href="mailto:truonggnfx13372@gmail.com" class="nav-link p-0 text-muted">Email: truonggnfx13372@gmail.com</a></li>
          <li class="nav-item mb-2"><a href="tel:+84938798685" class="nav-link p-0 text-muted">Phone: (+84) 938-798-685</a></li>
        </ul>
      </div>

      <div class="col-2 offset-4">
        <h5>Sitemap</h5>
        <ul class="nav flex-column">
          <li class="nav-item mb-2"><a href="<c:url value="/explore" />" class="nav-link p-0 text-muted">Explore</a></li>
          <li class="nav-item mb-2"><a href="<c:url value="/contact" />" class="nav-link p-0 text-muted">Contact</a></li>
        </ul>
      </div>
    </div>

    <div class="py-4 my-4 border-top">
      <p class="text-center">&#174;Copyright 2022</p>
    </div>
  </footer>
</div>
</body>
</html>