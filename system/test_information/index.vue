<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">ID</label>
        <el-input v-model="query.testInforId" clearable placeholder="ID" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">报检号</label>
        <el-input v-model="query.testId" clearable placeholder="报检号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">申请检测时间</label>
        <el-input v-model="query.testTime" clearable placeholder="申请检测时间" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">待检区段起始里程</label>
        <el-input v-model="query.testStartingDistance" clearable placeholder="待检区段起始里程" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">待检区段结束里程</label>
        <el-input v-model="query.testEndingDistance" clearable placeholder="待检区段结束里程" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">待检区段长度</label>
        <el-input v-model="query.testLength" clearable placeholder="待检区段长度" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">围岩类型</label>
        <el-input v-model="query.wallRockType" clearable placeholder="围岩类型" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">初支厚度</label>
        <el-input v-model="query.supportTickness" clearable placeholder="初支厚度" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">间距(m/榀)</label>
        <el-input v-model="query.separationDistance" clearable placeholder="间距(m/榀)" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">钢筋网间距</label>
        <el-input v-model="query.meshDistance" clearable placeholder="钢筋网间距" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">环向钢筋间距</label>
        <el-input v-model="query.annularBarDistance" clearable placeholder="环向钢筋间距" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">钢筋保护厚度</label>
        <el-input v-model="query.reinforPrtTickness" clearable placeholder="钢筋保护厚度" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">二次衬砌厚度-拱部</label>
        <el-input v-model="query.secLineArchTickness" clearable placeholder="二次衬砌厚度-拱部" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">二次衬砌厚度-边墙</label>
        <el-input v-model="query.secLineWallTickness" clearable placeholder="二次衬砌厚度-边墙" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">二次衬砌厚度-仰拱</label>
        <el-input v-model="query.secLineInverArchTickness" clearable placeholder="二次衬砌厚度-仰拱" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">二次衬砌厚度-填充</label>
        <el-input v-model="query.secLineFilerTickness" clearable placeholder="二次衬砌厚度-填充" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">项目名称</label>
        <el-input v-model="query.projectName" clearable placeholder="项目名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">标段名称</label>
        <el-input v-model="query.sectionName" clearable placeholder="标段名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">隧道名称</label>
        <el-input v-model="query.tunnelName" clearable placeholder="隧道名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">工点名称</label>
        <el-input v-model="query.worksiteName" clearable placeholder="工点名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">状态</label>
        <el-input v-model="query.statute" clearable placeholder="状态" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="ID">
            <el-input v-model="form.testInforId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="报检号">
            <el-input v-model="form.testId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="申请检测时间">
            <el-input v-model="form.testTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="待检区段起始里程">
            <el-input v-model="form.testStartingDistance" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="待检区段结束里程">
            <el-input v-model="form.testEndingDistance" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="待检区段长度">
            <el-input v-model="form.testLength" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="围岩类型">
            <el-input v-model="form.wallRockType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="初支厚度">
            <el-input v-model="form.supportTickness" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="间距(m/榀)">
            <el-input v-model="form.separationDistance" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="钢筋网间距">
            <el-input v-model="form.meshDistance" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="环向钢筋间距">
            <el-input v-model="form.annularBarDistance" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="钢筋保护厚度">
            <el-input v-model="form.reinforPrtTickness" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="二次衬砌厚度-拱部">
            <el-input v-model="form.secLineArchTickness" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="二次衬砌厚度-边墙">
            <el-input v-model="form.secLineWallTickness" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="二次衬砌厚度-仰拱">
            <el-input v-model="form.secLineInverArchTickness" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="二次衬砌厚度-填充">
            <el-input v-model="form.secLineFilerTickness" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="form.projectName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="标段名称">
            <el-input v-model="form.sectionName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="隧道名称">
            <el-input v-model="form.tunnelName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="工点名称">
            <el-input v-model="form.worksiteName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="状态">
            <el-input v-model="form.statute" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu1">
            <el-input v-model="form.beizhu1" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu2">
            <el-input v-model="form.beizhu2" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu3">
            <el-input v-model="form.beizhu3" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu4">
            <el-input v-model="form.beizhu4" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu5">
            <el-input v-model="form.beizhu5" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu6">
            <el-input v-model="form.beizhu6" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu7">
            <el-input v-model="form.beizhu7" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu8">
            <el-input v-model="form.beizhu8" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu9">
            <el-input v-model="form.beizhu9" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu10">
            <el-input v-model="form.beizhu10" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu11">
            <el-input v-model="form.beizhu11" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu12">
            <el-input v-model="form.beizhu12" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu13">
            <el-input v-model="form.beizhu13" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu14">
            <el-input v-model="form.beizhu14" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu15">
            <el-input v-model="form.beizhu15" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="testInforId" label="ID" />
        <el-table-column prop="testId" label="报检号" />
        <el-table-column prop="testTime" label="申请检测时间" />
        <el-table-column prop="testStartingDistance" label="待检区段起始里程" />
        <el-table-column prop="testEndingDistance" label="待检区段结束里程" />
        <el-table-column prop="testLength" label="待检区段长度" />
        <el-table-column prop="wallRockType" label="围岩类型" />
        <el-table-column prop="supportTickness" label="初支厚度" />
        <el-table-column prop="separationDistance" label="间距(m/榀)" />
        <el-table-column prop="meshDistance" label="钢筋网间距" />
        <el-table-column prop="annularBarDistance" label="环向钢筋间距" />
        <el-table-column prop="reinforPrtTickness" label="钢筋保护厚度" />
        <el-table-column prop="secLineArchTickness" label="二次衬砌厚度-拱部" />
        <el-table-column prop="secLineWallTickness" label="二次衬砌厚度-边墙" />
        <el-table-column prop="secLineInverArchTickness" label="二次衬砌厚度-仰拱" />
        <el-table-column prop="secLineFilerTickness" label="二次衬砌厚度-填充" />
        <el-table-column prop="projectName" label="项目名称" />
        <el-table-column prop="sectionName" label="标段名称" />
        <el-table-column prop="tunnelName" label="隧道名称" />
        <el-table-column prop="worksiteName" label="工点名称" />
        <el-table-column prop="statute" label="状态" />
        <el-table-column prop="beizhu1" label="beizhu1" />
        <el-table-column prop="beizhu2" label="beizhu2" />
        <el-table-column prop="beizhu3" label="beizhu3" />
        <el-table-column prop="beizhu4" label="beizhu4" />
        <el-table-column prop="beizhu5" label="beizhu5" />
        <el-table-column prop="beizhu6" label="beizhu6" />
        <el-table-column prop="beizhu7" label="beizhu7" />
        <el-table-column prop="beizhu8" label="beizhu8" />
        <el-table-column prop="beizhu9" label="beizhu9" />
        <el-table-column prop="beizhu10" label="beizhu10" />
        <el-table-column prop="beizhu11" label="beizhu11" />
        <el-table-column prop="beizhu12" label="beizhu12" />
        <el-table-column prop="beizhu13" label="beizhu13" />
        <el-table-column prop="beizhu14" label="beizhu14" />
        <el-table-column prop="beizhu15" label="beizhu15" />
        <el-table-column v-if="checkPer(['admin','testInformation:edit','testInformation:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudTestInformation from '@/api/testInformation'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { testInforId: null, testId: null, testTime: null, testStartingDistance: null, testEndingDistance: null, testLength: null, wallRockType: null, supportTickness: null, separationDistance: null, meshDistance: null, annularBarDistance: null, reinforPrtTickness: null, secLineArchTickness: null, secLineWallTickness: null, secLineInverArchTickness: null, secLineFilerTickness: null, projectName: null, sectionName: null, tunnelName: null, worksiteName: null, statute: null, beizhu1: null, beizhu2: null, beizhu3: null, beizhu4: null, beizhu5: null, beizhu6: null, beizhu7: null, beizhu8: null, beizhu9: null, beizhu10: null, beizhu11: null, beizhu12: null, beizhu13: null, beizhu14: null, beizhu15: null }
export default {
  name: 'TestInformation',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/test_information', url: 'api/testInformation', idField: 'testInforId', sort: 'testInforId,desc', crudMethod: { ...crudTestInformation }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'testInformation:add'],
        edit: ['admin', 'testInformation:edit'],
        del: ['admin', 'testInformation:del']
      },
      rules: {
      },
      queryTypeOptions: [
        { key: 'testInforId', display_name: 'ID' },
        { key: 'testId', display_name: '报检号' },
        { key: 'testTime', display_name: '申请检测时间' },
        { key: 'testStartingDistance', display_name: '待检区段起始里程' },
        { key: 'testEndingDistance', display_name: '待检区段结束里程' },
        { key: 'testLength', display_name: '待检区段长度' },
        { key: 'wallRockType', display_name: '围岩类型' },
        { key: 'supportTickness', display_name: '初支厚度' },
        { key: 'separationDistance', display_name: '间距(m/榀)' },
        { key: 'meshDistance', display_name: '钢筋网间距' },
        { key: 'annularBarDistance', display_name: '环向钢筋间距' },
        { key: 'reinforPrtTickness', display_name: '钢筋保护厚度' },
        { key: 'secLineArchTickness', display_name: '二次衬砌厚度-拱部' },
        { key: 'secLineWallTickness', display_name: '二次衬砌厚度-边墙' },
        { key: 'secLineInverArchTickness', display_name: '二次衬砌厚度-仰拱' },
        { key: 'secLineFilerTickness', display_name: '二次衬砌厚度-填充' },
        { key: 'projectName', display_name: '项目名称' },
        { key: 'sectionName', display_name: '标段名称' },
        { key: 'tunnelName', display_name: '隧道名称' },
        { key: 'worksiteName', display_name: '工点名称' },
        { key: 'statute', display_name: '状态' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
